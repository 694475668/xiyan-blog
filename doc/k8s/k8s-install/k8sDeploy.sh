#!/bin/bash
#b8_yang@163.com
#source ./base.config
bash_path=$(cd "$(dirname "$0")";pwd)
source $bash_path/base.config

if [[ "$(whoami)" != "root" ]]; then
	echo "please run this script as root ." >&2
	exit 1
fi

# log="./setup.log"  #操作日志存放路径
# fsize=2000000
# exec 2>>$log  #如果执行过程中有错误信息均输出到日志文件中

echo -e "\033[31m 这个是k8s集群脚本！欢迎加入我的QQ群557423713，博主博客http://xiyanit.cn,公众号目前还在建造后期在更新 ！Please continue to enter or ctrl+C to cancel \033[0m"
sleep 5
#yum update
yum_update(){
	yum update -y
}
#configure yum source
yum_config(){
  yum install wget epel-release -y
  if [[ $aliyun == "1" ]];then
  test -d /etc/yum.repos.d/bak/ || yum install wget epel-release -y && cd /etc/yum.repos.d/ && mkdir bak && mv -f *.repo bak/ && wget -O /etc/yum.repos.d/CentOS-Base.repo http://mirrors.aliyun.com/repo/Centos-7.repo && wget -O /etc/yum.repos.d/epel.repo http://mirrors.aliyun.com/repo/epel-7.repo && yum clean all && yum makecache
  fi
}

yum_init(){
num=0
while true ; do
let num+=1
yum -y install iotop iftop  sshpass yum-utils net-tools git lrzsz expect gcc gcc-c++ make cmake libxml2-devel openssl-devel curl curl-devel unzip sudo ntp libaio-devel wget vim ncurses-devel autoconf automake zlib-devel  python-devel bash-completion
if [[ $? -eq 0 ]] ; then
echo "初始化安装环境配置完成！！！"
break;
else
if [[ num -gt 3 ]];then
echo "你登录 "$masterip" 瞅瞅咋回事？一直无法yum包"
break
fi
echo "FK!~没成功？哥再来一次！！"
fi
done
}

#firewalld
iptables_config(){
if [[ `ps -ef | grep firewalld |wc -l` -gt 1 ]];then
  systemctl stop firewalld.service
  systemctl disable firewalld.service
  echo "防火墙我关了奥！！！"
fi
#  iptables -P FORWARD ACCEPT
}
#system config
system_config(){
grep "SELINUX=disabled" /etc/selinux/config
if [[ $? -eq 0 ]];then
  echo "SELINUX 已经禁用！！"
else
  sed -i "s/SELINUX=enforcing/SELINUX=disabled/g" /etc/selinux/config
  setenforce 0
  echo "SELINUX 已经禁用！！"
fi

if [[ `ps -ef | grep chrony |wc -l` -eq 1 ]];then
  timedatectl set-local-rtc 1 && timedatectl set-timezone Asia/Shanghai
  yum -y install chrony && systemctl start chronyd.service && systemctl enable chronyd.service
  systemctl restart chronyd.service
  echo "时钟同步chrony服务安装完毕！！"
fi
}

init_bridge(){
echo "1" > /proc/sys/net/bridge/bridge-nf-call-iptables
echo "调整网桥参数完成！！！"
}


ulimit_config(){
grep 'ulimit' /etc/rc.local
if [[ $? -eq 0 ]];then
echo "内核参数调整完毕！！！"
else
  echo "ulimit -SHn 102400" >> /etc/rc.local
  cat >> /etc/security/limits.conf << EOF
  *           soft   nofile       102400
  *           hard   nofile       102400
  *           soft   nproc        102400
  *           hard   nproc        102400
  *           soft  memlock      unlimited 
  *           hard  memlock      unlimited
EOF
  cat >> /etc/sysctl.conf << EOF
    kernel.pid_max=4194303
EOF
sysctl -p
echo "内核参数调整完毕！！！"
fi
}

ssh_config(){
grep 'UserKnownHostsFile' /etc/ssh/ssh_config
if [[ $? -eq 0 ]];then
echo "ssh参数配置完毕！！！"
else
sed -i "2i StrictHostKeyChecking no\nUserKnownHostsFile /dev/null" /etc/ssh/ssh_config
echo "ssh参数配置完毕！！！"
fi
}



get_localip(){
ipaddr=$(ip addr | awk '/^[0-9]+: / {}; /inet.*global/ {print gensub(/(.*)\/(.*)/, "\\1", "g", $2)}' | grep $ip_segment)
echo "$ipaddr"
}


change_hosts(){
cd $bash_path
num=0
for host in ${hostip[@]}
do
grep "$host" /etc/hosts
if [[ $? -eq 0 ]];then

echo "hosts修改完毕！！！"
else
let num+=1

if [[ $host == `get_localip` ]];then
`hostnamectl set-hostname $hostname$num`
grep "$host" /etc/hosts || echo $host `hostname` >> /etc/hosts
else
grep "$host" /etc/hosts || echo $host $hostname$num >> /etc/hosts
fi

fi
done

}

#install docker
install_docker() {
test -d /etc/docker
if [[ $? -eq 0 ]];then
echo "docker已经安装完毕!!!"
else
mkdir -p /etc/docker
yum-config-manager --add-repo  https://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
yum install -y --setopt=obsoletes=0 docker-ce
tee /etc/docker/daemon.json <<-'EOF'
{
  "registry-mirrors": ["https://gpkhi0nk.mirror.aliyuncs.com"]
}
EOF
systemctl daemon-reload
systemctl enable docker
systemctl restart docker
echo "docker已经安装完毕!!!"
fi
}

#swapoff
swapoff(){
grep 'vm.swappiness=0' /etc/sysctl.conf
if [[ $? -eq 0 ]];then
echo "临时命名空间删除！！！"
else
  /sbin/swapoff -a
  sed -i '/ swap / s/^\(.*\)$/#\1/g' /etc/fstab
  echo "vm.swappiness=0" >> /etc/sysctl.conf
  /sbin/sysctl -p
fi
}


set_repo(){
test -f /etc/yum.repos.d/kubernetes.repo
if [[ $? -eq 0 ]];then
echo "kubelet kubectl kubeadm安装完毕！！!"
else
cat <<EOF > /etc/yum.repos.d/kubernetes.repo
[kubernetes]
name=Kubernetes
baseurl=https://mirrors.aliyun.com/kubernetes/yum/repos/kubernetes-el7-x86_64
enabled=1
gpgcheck=0
repo_gpgcheck=0
gpgkey=https://mirrors.aliyun.com/kubernetes/yum/doc/yum-key.gpg https://mirrors.aliyun.com/kubernetes/yum/doc/rpm-package-key.gpg
EOF
	yum -y install kubelet-${k8s_version} kubeadm-${k8s_version} kubectl-${k8s_version} kubernetes-cni
	yum list installed | grep kube
	systemctl daemon-reload
	systemctl enable kubelet
	systemctl start kubelet
fi
}

install_masterk8s(){
	images=(kube-scheduler:v${k8s_version}
			kube-proxy:v${k8s_version}
			kube-controller-manager:v${k8s_version}
			kube-apiserver:v${k8s_version}
			pause:3.2
			etcd:3.4.13-0)
	for imagename in ${images[@]}; do
    
    num=0
    while true ; do
    let num+=1
	docker pull registry.aliyuncs.com/google_containers/$imagename
    if [[ $? -eq 0 ]] ; then
    echo "registry.aliyuncs.com/google_containers/$imagename 下载完成"
	docker tag registry.aliyuncs.com/google_containers/$imagename k8s.gcr.io/$imagename
	docker rmi registry.aliyuncs.com/google_containers/$imagename
    break;
    else
    if [[ num -gt 5 ]];then
    echo "registry.aliyuncs.com/google_containers/$imagename 无法下载，请查看网络！"
    break
    fi
    echo "FK!~ registry.aliyuncs.com/google_containers/$imagename 没成功？哥再来一次！！"
    fi
    done
    

	done
	docker pull registry.aliyuncs.com/google_containers/coredns:1.7.0
	docker tag registry.aliyuncs.com/google_containers/coredns:1.7.0 k8s.gcr.io/coredns:1.7.0
	docker rmi registry.aliyuncs.com/google_containers/coredns:1.7.0
        docker pull quay.io/coreos/flannel:v0.13.0-amd64
}


# config docker
config_docker(){
grep "tcp://0.0.0.0:2375" /usr/lib/systemd/system/docker.service
if [[ $? -eq 0 ]];then
echo "docker API接口已经配置完毕"
else
sed -i "/^ExecStart/cExecStart=\/usr\/bin\/dockerd -H tcp:\/\/0\.0\.0\.0:2375 -H unix:\/\/\/var\/run\/docker.sock" /usr/lib/systemd/system/docker.service
systemctl daemon-reload
systemctl restart docker.service
echo "docker API接口已经配置完毕"
fi
}


init_k8s(){
	set -e
	rm -rf /root/.kube
    rm -rf /var/lib/etcd/*
	kubeadm reset -f
	kubeadm init --kubernetes-version=v$k8s_version --pod-network-cidr=10.244.0.0/16 --apiserver-advertise-address=$masterip
	mkdir -p /root/.kube
	cp /etc/kubernetes/admin.conf /root/.kube/config
	chown $(id -u):$(id -g) /root/.kube/config
	cp -p /root/.bash_profile /root/.bash_profile.bak$(date '+%Y%m%d%H%M%S')
	echo "export KUBECONFIG=/root/.kube/config" >> /root/.bash_profile
	source /root/.bash_profile
}

install_flannel(){
    cd $bash_path
#    test -f kube-flannel.yml || wget https://raw.githubusercontent.com/coreos/flannel/master/Documentation/kube-flannel.yml
	kubectl apply -f kube-flannel.yml
    echo "flannel 网络配置完毕"
}

token_shar_value(){

    cd $bash_path
    /usr/bin/kubeadm token list > $bash_path/token_shar_value.text
    sed -i "s/tocken=/tocken=$(sed -n "2, 1p" token_shar_value.text | awk '{print $1}')/g" $bash_path/base.config
    sed -i "s/sha_value=/sha_value=$(openssl x509 -pubkey -in /etc/kubernetes/pki/ca.crt | openssl rsa -pubin -outform der 2>/dev/null | openssl dgst -sha256 -hex | sed 's/^.* //')/g" $bash_path/base.config
        
    rm -rf $bash_path/token_shar_value.text
    echo "tocken 设置完毕"
}


rootssh_trust(){
cd $bash_path
yum -y install sshpass
num=0
for host in ${hostip[@]}
do
let num+=1
if [[ `get_localip` != $host ]];then

if [[ ! -f /root/.ssh/id_rsa.pub ]];then
echo '###########init'
expect ssh_trust_init.exp
sshpass -p $root_passwd ssh-copy-id $host

else
echo '###########add'
sshpass -p $root_passwd ssh-copy-id $host
fi
scp base.config kube-flannel.yml hwclock_ntp.sh node_install_k8s.sh ssh_trust_init.exp root@$host:/root && scp /etc/hosts root@$host:/etc/hosts && ssh root@$host "hostnamectl set-hostname $hostname$num" && ssh root@$host /root/hwclock_ntp.sh && ssh root@$host /root/node_install_k8s.sh && ssh root@$host "rm -rf base.config node_install_k8s.sh ssh_trust_init.exp hwclock_ntp.sh kube-flannel.yml"
fi
echo $host"服务器安装完毕！！！ "
done
}

check_cluster(){
sleep 30s
kubectl get node 
kubectl cluster-info

}


main(){
 #yum_update
  yum_config
  yum_init
  ssh_config
  iptables_config

  system_config
  ulimit_config
  change_hosts
  swapoff
  install_docker
  #config_docker
  set_repo
  init_bridge
  install_masterk8s
  init_k8s
  
  install_flannel
  token_shar_value
  
  rootssh_trust
  check_cluster
echo "k8s_$k8s_version 集群已经安装完毕，请登录相关服务器验收！"
}
main 

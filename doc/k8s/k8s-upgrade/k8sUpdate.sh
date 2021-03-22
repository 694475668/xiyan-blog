#!/bin/bash
#b8_yang@163.com
#source ./base.config
bash_path=$(cd "$(dirname "$0")";pwd)
source $bash_path/base.config

if [[ "$(whoami)" != "root" ]]; then
	echo "please run this script as root ." >&2
	exit 1
fi



yum list --showduplicates kubeadm --disableexcludes=kubernetes
echo -e "\033[31m 你好好核对一下你写的版本号对不对？如果有问题ctrl+C停止脚本重新编写参数，欢迎加入我的QQ群557423713，博主博客http://xiyanit.cn,公众号目前还在建造后期在更新 Please continue to enter or ctrl+C to cancel \033[0m"
sleep 5
#yum update

get_localip(){
ipaddr=$(ip addr | awk '/^[0-9]+: / {}; /inet.*global/ {print gensub(/(.*)\/(.*)/, "\\1", "g", $2)}' | grep $ip_segment)
echo "$ipaddr"
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
gpgcheck=1
repo_gpgcheck=1
gpgkey=https://mirrors.aliyun.com/kubernetes/yum/doc/yum-key.gpg https://mirrors.aliyun.com/kubernetes/yum/doc/rpm-package-key.gpg
EOF
	yum -y install kubelet-${kubeadm_version} kubeadm-${kubeadm_version} kubectl-${kubeadm_version} --disableexcludes=kubernetes
	yum list installed | grep kube
	systemctl daemon-reload
	systemctl enable kubelet
	systemctl start kubelet
fi
}

install_masterk8s(){
	images=(kube-scheduler:v${kubeadm_version}
			kube-proxy:v${kubeadm_version}
			kube-controller-manager:v${kubeadm_version}
			kube-apiserver:v${kubeadm_version}
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
    echo "registry.aliyuncs.com/google_containers/$imagename 无法下载？哥再来一次！！"
    fi
    done
    
	done
	docker pull registry.aliyuncs.com/google_containers/coredns:1.7.0
	docker tag registry.aliyuncs.com/google_containers/coredns:1.7.0 k8s.gcr.io/coredns:1.7.0
	docker rmi registry.aliyuncs.com/google_containers/coredns:1.7.0
        docker pull quay.io/coreos/flannel:v0.13.0-amd64
}


init_k8s(){
yum install -y kubeadm-${kubeadm_version}-0 --disableexcludes=kubernetes
expect kuberadm_upgrade.exp ${kubeadm_version}
yum install -y kubelet-${kubeadm_version}-0 kubectl-${kubeadm_version}-0 --disableexcludes=kubernetes
systemctl daemon-reload
systemctl restart kubelet
}



rootssh_trust(){
cd $bash_path
#yum -y install sshpass
num=0
for host in ${hostip[@]}
do
let num+=1
if [[ `get_localip` != $host ]];then
scp /bin/kubeadm root@$host:/bin && scp base.config node_update_k8s.sh root@$host:/root && kubectl drain $hostname$num --ignore-daemonsets && ssh root@$host /root/node_update_k8s.sh && ssh root@$host "rm -rf base.config node_update_k8s.sh" 
kubectl uncordon $hostname$num
fi
echo $host"服务器升级完毕！！！ "
done
}

check_cluster(){
sleep 30s
kubectl get node 
kubectl cluster-info

}


main(){
  install_masterk8s
  init_k8s
  
  set_repo

  rootssh_trust
  check_cluster
  echo "k8s"k8s_version" 集群已经升级完毕，请登录相关服务器验收！"
}
main

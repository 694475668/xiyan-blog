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

echo -e "\033[31m 这个是k8s集群脚本！欢迎加入我的QQ群557423713，博主博客http://xiyanit.cn,公众号目前还在建造后期在更新！Please continue to enter or ctrl+C to cancel \033[0m"
sleep 5



get_localip(){
ipaddr=$(ip addr | awk '/^[0-9]+: / {}; /inet.*global/ {print gensub(/(.*)\/(.*)/, "\\1", "g", $2)}' | grep $ip_segment)
echo "$ipaddr"
}


change_hosts(){
cd $bash_path
num=$nodenum
for host in ${hostip[@]}
do
grep "$host" /etc/hosts
if [[ $? -eq 0 ]];then

echo "hosts修改完毕！！！"
else


if [[ $host == `get_localip` ]];then
`hostnamectl set-hostname $hostname$num`
grep "$host" /etc/hosts || echo $host `hostname` >> /etc/hosts
else
grep "$host" /etc/hosts || echo $host $hostname$num >> /etc/hosts
fi

fi
done

}



token_shar_value(){

    cd $bash_path
    /usr/bin/kubeadm token create
    /usr/bin/kubeadm token list > $bash_path/token_shar_value.text
    sed -i "s/tocken=/tocken=$(sed -n "2, 1p" token_shar_value.text | awk '{print $1}')/g" $bash_path/base.config
    sed -i "s/sha_value=/sha_value=$(openssl x509 -pubkey -in /etc/kubernetes/pki/ca.crt | openssl rsa -pubin -outform der 2>/dev/null | openssl dgst -sha256 -hex | sed 's/^.* //')/g" $bash_path/base.config
        
    rm -rf $bash_path/token_shar_value.text
    echo "tocken 设置完毕"
}


rootssh_trust(){
cd $bash_path
yum -y install sshpass
for host in ${hostip[@]}
do
if [[ `get_localip` != $host ]];then

if [[ ! -f /root/.ssh/id_rsa.pub ]];then
echo '###########init'
expect ssh_trust_init.exp
sshpass -p $root_passwd ssh-copy-id $host

else
echo '###########add'
sshpass -p $root_passwd ssh-copy-id $host
fi
scp base.config kube-flannel.yml hwclock_ntp.sh node_install_k8s.sh root@$host:/root  && ssh root@$host "hostnamectl set-hostname $hostname$nodenum" && ssh root@$host /root/hwclock_ntp.sh && ssh root@$host /root/node_install_k8s.sh && ssh root@$host "rm -rf base.config node_install_k8s.sh  hwclock_ntp.sh kube-flannel.yml"
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

  change_hosts
  token_shar_value
  rootssh_trust
  check_cluster
  echo "k8s_$k8s_version 集群已经安装完毕，请登录相关服务器验收！"
}
main 

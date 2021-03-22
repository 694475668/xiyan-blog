#!/bin/bash
#b8_yang@163.com
bash_path=$(cd "$(dirname "$0")";pwd)
source $bash_path/base.config

if [[ "$(whoami)" != "root" ]]; then
	echo "please run this script as root ." >&2
	exit 1
fi

log="./setup.log"  #操作日志存放路径
fsize=2000000
exec 2>>$log  #如果执行过程中有错误信息均输出到日志文件中

echo -e "\033[31m 这个是k8s集群升级脚本，node节点正在运行脚本中,请不要刷新或断开连接，结束会有相关提示！欢迎加入我的QQ群557423713，博主博客http://xiyanit.cn,公众号目前还在建造后期在更新  \033[0m"
#sleep 5
#yum update




get_localip(){
ipaddr=$(ip addr | awk '/^[0-9]+: / {}; /inet.*global/ {print gensub(/(.*)\/(.*)/, "\\1", "g", $2)}' | grep $ip_segment)
echo "$ipaddr"
}



set_repo(){
test -f /etc/yum.repos.d/kubernetes.repo
if [[ $? -eq 0 ]];then
echo "kubelet kubectl kubeadm升级完毕！！!"
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
	yum -y install kubeadm-${kubeadm_version} kubelet-${kubeadm_version} --disableexcludes=kubernetes
#	yum list installed | grep kube
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
#	docker rmi registry.aliyuncs.com/google_containers/$imagename
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

updateimage(){
    
    yum install -y kubelet-${kubeadm_version}-0 kubectl-${kubeadm_version}-0 --disableexcludes=kubernetes
    kubeadm upgrade node config --kubelet-version ${k8s_version}
    systemctl daemon-reload
    systemctl restart kubelet
    
}

clear(){
cd $bash_path
rm -rf base.config node_update_k8s.sh
}

main(){
 set_repo
 install_masterk8s
 updateimage

 echo `hostname`" 节点已经升级完毕"
 
 kubectl get node 
 clear
}
main > ./setup.log 2>&1

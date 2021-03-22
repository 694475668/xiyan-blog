#!/bin/bash
source ./base.config
yum -y install chrony
grep "$masterip" /etc/chrony.conf
if [[  $? == 0  ]];then
echo "NTP配置完毕"
else
sed -i "7i server  $masterip iburst\nallow $cluster_network" /etc/chrony.conf
echo "NTP配置完毕"
fi
systemctl start chronyd.service && systemctl enable chronyd.service
systemctl restart chronyd.service

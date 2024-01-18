volume_type="StandardSSD_LRS"
instance_count=2
instance_type="Standard_B1ms"
admin_username="ubuntu"
subnet_id= "default"
key_name="nextgen-devops-team"
volume_size=25
rg="Intel-RFI-RG"
vnet="vmicreate-vnet"
siro="0001-com-ubuntu-server-jammy"
sirp="Canonical"
sirs="22_04-lts-gen2"
sirv="latest"
admin_ssh_key=[
username =ubuntu
public_key = file("./nextgen.pub")
]

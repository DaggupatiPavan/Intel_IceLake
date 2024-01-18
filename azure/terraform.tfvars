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
admin_ssh_key = [
  {
    username   = "ubuntu"
    public_key = "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQCHCckIyC/GeVakw03fnU/xDySwEF9EITm69rP0O9CzXg4ZTRRqdPEkiOAEcTiwFPvI0H3EDmuRNHNF32VFnz7b535VUuD+TiwTrK7XoxzeIyTFw0icMWxvgj9lNDedknXPF9AvdxZf0NeYifbglTkFoQmWUKz2i8GAFEYfOUS1e99akn1Fcj279LSihq3kszLlgX8FIJQQUf2oh6+j1ZQivsuCWwVm/BXcHfQkLfV3AeiWeSxwMC6DIwcX18msRHFGbvaXnUS25Ba1RfGmJJ0rIpYQZ7l8ycWXIuTrRNgSCJhHh1jVbhNIe1jXhhVR8NA91J7AK5pEG+oZDofGW4Of"
  }
]

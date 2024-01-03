output "private_ips" {
  value=module.ec2-vm.*.private_ip
}

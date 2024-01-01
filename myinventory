#!/bin/bash

# Path to the Terraform state file
TF_STATE_FILE="terraform.tfstate"

# Extract IP addresses from the Terraform output
IP_ADDRESSES=($(terraform output -json instance_ips | jq -r '.value[]'))

# Create or truncate the Ansible inventory file
> inventory

# Write IP addresses with specific hostnames and "ubuntu@" prefix to the Ansible inventory file
echo "postgres ansible_ssh_host=ubuntu@${IP_ADDRESSES[0]}" >> inventory
echo "hammer ansible_ssh_host=ubuntu@${IP_ADDRESSES[1]}" >> inventory

# Print the Ansible inventory file content
cat inventory

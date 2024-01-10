module "azure-vm" {
  source                              = "intel/azure-linux-vm/intel"
  azurerm_resource_group_name         = var.rg
  admin_password                      = "Test@123"
  azurerm_virtual_network_name        = var.vnet
  azurerm_subnet_name                 = var.subnet_id
  virtual_network_resource_group_name = var.rg
  os_disk_storage_account_type        = var.volume_type
  virtual_machine_size                = var.instance_type
  source_image_reference_offer        = var.siro
  source_image_reference_publisher    = var.sirp
  source_image_reference_sku          = var.sirs
  source_image_reference_version      = var.sirv
}

import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class DeviceComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-device div table .btn-danger'));
  title = element.all(by.css('jhi-device div h2#page-heading span')).first();

  async clickOnCreateButton(timeout?: number) {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(timeout?: number) {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons() {
    return this.deleteButtons.count();
  }

  async getTitle() {
    return this.title.getText();
  }
}

export class DeviceUpdatePage {
  pageTitle = element(by.id('jhi-device-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  deviceIdInput = element(by.id('field_deviceId'));
  ownerIdInput = element(by.id('field_ownerId'));
  createdInput = element(by.id('field_created'));
  purchasedInput = element(by.id('field_purchased'));

  async getPageTitle() {
    return this.pageTitle.getText();
  }

  async setDeviceIdInput(deviceId) {
    await this.deviceIdInput.sendKeys(deviceId);
  }

  async getDeviceIdInput() {
    return await this.deviceIdInput.getAttribute('value');
  }

  async setOwnerIdInput(ownerId) {
    await this.ownerIdInput.sendKeys(ownerId);
  }

  async getOwnerIdInput() {
    return await this.ownerIdInput.getAttribute('value');
  }

  async setCreatedInput(created) {
    await this.createdInput.sendKeys(created);
  }

  async getCreatedInput() {
    return await this.createdInput.getAttribute('value');
  }

  async setPurchasedInput(purchased) {
    await this.purchasedInput.sendKeys(purchased);
  }

  async getPurchasedInput() {
    return await this.purchasedInput.getAttribute('value');
  }

  async save(timeout?: number) {
    await this.saveButton.click();
  }

  async cancel(timeout?: number) {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class DeviceDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-device-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-device'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}

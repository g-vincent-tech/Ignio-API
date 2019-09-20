import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class DeviceDataComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-device-data div table .btn-danger'));
  title = element.all(by.css('jhi-device-data div h2#page-heading span')).first();

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

export class DeviceDataUpdatePage {
  pageTitle = element(by.id('jhi-device-data-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  deviceIdInput = element(by.id('field_deviceId'));
  epochInput = element(by.id('field_epoch'));

  async getPageTitle() {
    return this.pageTitle.getText();
  }

  async setDeviceIdInput(deviceId) {
    await this.deviceIdInput.sendKeys(deviceId);
  }

  async getDeviceIdInput() {
    return await this.deviceIdInput.getAttribute('value');
  }

  async setEpochInput(epoch) {
    await this.epochInput.sendKeys(epoch);
  }

  async getEpochInput() {
    return await this.epochInput.getAttribute('value');
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

export class DeviceDataDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-deviceData-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-deviceData'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}

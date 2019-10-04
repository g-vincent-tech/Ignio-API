import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class SensorDataComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-sensor-data div table .btn-danger'));
  title = element.all(by.css('jhi-sensor-data div h2#page-heading span')).first();

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

export class SensorDataUpdatePage {
  pageTitle = element(by.id('jhi-sensor-data-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  deviceIdInput = element(by.id('field_deviceId'));
  temperatureInput = element(by.id('field_temperature'));
  co_ppmInput = element(by.id('field_co_ppm'));
  lp_gas_ppmInput = element(by.id('field_lp_gas_ppm'));
  particle_ppmInput = element(by.id('field_particle_ppm'));
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

  async setTemperatureInput(temperature) {
    await this.temperatureInput.sendKeys(temperature);
  }

  async getTemperatureInput() {
    return await this.temperatureInput.getAttribute('value');
  }

  async setCo_ppmInput(co_ppm) {
    await this.co_ppmInput.sendKeys(co_ppm);
  }

  async getCo_ppmInput() {
    return await this.co_ppmInput.getAttribute('value');
  }

  async setLp_gas_ppmInput(lp_gas_ppm) {
    await this.lp_gas_ppmInput.sendKeys(lp_gas_ppm);
  }

  async getLp_gas_ppmInput() {
    return await this.lp_gas_ppmInput.getAttribute('value');
  }

  async setParticle_ppmInput(particle_ppm) {
    await this.particle_ppmInput.sendKeys(particle_ppm);
  }

  async getParticle_ppmInput() {
    return await this.particle_ppmInput.getAttribute('value');
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

export class SensorDataDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-sensorData-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-sensorData'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}

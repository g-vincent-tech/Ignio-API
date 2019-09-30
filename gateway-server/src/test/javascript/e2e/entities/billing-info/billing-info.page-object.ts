import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class BillingInfoComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-billing-info div table .btn-danger'));
  title = element.all(by.css('jhi-billing-info div h2#page-heading span')).first();

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

export class BillingInfoUpdatePage {
  pageTitle = element(by.id('jhi-billing-info-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  igniosInput = element(by.id('field_ignios'));
  creditCardNumberInput = element(by.id('field_creditCardNumber'));
  creditCardTypeInput = element(by.id('field_creditCardType'));
  cvv2Input = element(by.id('field_cvv2'));
  expiresOnInput = element(by.id('field_expiresOn'));
  billingAddressInput = element(by.id('field_billingAddress'));
  cityInput = element(by.id('field_city'));
  countryInput = element(by.id('field_country'));
  postalCodeInput = element(by.id('field_postalCode'));

  async getPageTitle() {
    return this.pageTitle.getText();
  }

  async setIgniosInput(ignios) {
    await this.igniosInput.sendKeys(ignios);
  }

  async getIgniosInput() {
    return await this.igniosInput.getAttribute('value');
  }

  async setCreditCardNumberInput(creditCardNumber) {
    await this.creditCardNumberInput.sendKeys(creditCardNumber);
  }

  async getCreditCardNumberInput() {
    return await this.creditCardNumberInput.getAttribute('value');
  }

  async setCreditCardTypeInput(creditCardType) {
    await this.creditCardTypeInput.sendKeys(creditCardType);
  }

  async getCreditCardTypeInput() {
    return await this.creditCardTypeInput.getAttribute('value');
  }

  async setCvv2Input(cvv2) {
    await this.cvv2Input.sendKeys(cvv2);
  }

  async getCvv2Input() {
    return await this.cvv2Input.getAttribute('value');
  }

  async setExpiresOnInput(expiresOn) {
    await this.expiresOnInput.sendKeys(expiresOn);
  }

  async getExpiresOnInput() {
    return await this.expiresOnInput.getAttribute('value');
  }

  async setBillingAddressInput(billingAddress) {
    await this.billingAddressInput.sendKeys(billingAddress);
  }

  async getBillingAddressInput() {
    return await this.billingAddressInput.getAttribute('value');
  }

  async setCityInput(city) {
    await this.cityInput.sendKeys(city);
  }

  async getCityInput() {
    return await this.cityInput.getAttribute('value');
  }

  async setCountryInput(country) {
    await this.countryInput.sendKeys(country);
  }

  async getCountryInput() {
    return await this.countryInput.getAttribute('value');
  }

  async setPostalCodeInput(postalCode) {
    await this.postalCodeInput.sendKeys(postalCode);
  }

  async getPostalCodeInput() {
    return await this.postalCodeInput.getAttribute('value');
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

export class BillingInfoDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-billingInfo-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-billingInfo'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}

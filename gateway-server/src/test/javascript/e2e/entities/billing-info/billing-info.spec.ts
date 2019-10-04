/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { BillingInfoComponentsPage, BillingInfoDeleteDialog, BillingInfoUpdatePage } from './billing-info.page-object';

const expect = chai.expect;

describe('BillingInfo e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let billingInfoUpdatePage: BillingInfoUpdatePage;
  let billingInfoComponentsPage: BillingInfoComponentsPage;
  let billingInfoDeleteDialog: BillingInfoDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load BillingInfos', async () => {
    await navBarPage.goToEntity('billing-info');
    billingInfoComponentsPage = new BillingInfoComponentsPage();
    await browser.wait(ec.visibilityOf(billingInfoComponentsPage.title), 5000);
    expect(await billingInfoComponentsPage.getTitle()).to.eq('Billing Infos');
  });

  it('should load create BillingInfo page', async () => {
    await billingInfoComponentsPage.clickOnCreateButton();
    billingInfoUpdatePage = new BillingInfoUpdatePage();
    expect(await billingInfoUpdatePage.getPageTitle()).to.eq('Create or edit a Billing Info');
    await billingInfoUpdatePage.cancel();
  });

  it('should create and save BillingInfos', async () => {
    const nbButtonsBeforeCreate = await billingInfoComponentsPage.countDeleteButtons();

    await billingInfoComponentsPage.clickOnCreateButton();
    await promise.all([
      billingInfoUpdatePage.setIgniosInput('ignios'),
      billingInfoUpdatePage.setCreditCardNumberInput('5'),
      billingInfoUpdatePage.setCreditCardTypeInput('creditCardType'),
      billingInfoUpdatePage.setCvv2Input('cvv2'),
      billingInfoUpdatePage.setExpiresOnInput('expiresOn'),
      billingInfoUpdatePage.setBillingAddressInput('billingAddress'),
      billingInfoUpdatePage.setCityInput('city'),
      billingInfoUpdatePage.setCountryInput('country'),
      billingInfoUpdatePage.setPostalCodeInput('5')
    ]);
    expect(await billingInfoUpdatePage.getIgniosInput()).to.eq('ignios', 'Expected Ignios value to be equals to ignios');
    expect(await billingInfoUpdatePage.getCreditCardNumberInput()).to.eq('5', 'Expected creditCardNumber value to be equals to 5');
    expect(await billingInfoUpdatePage.getCreditCardTypeInput()).to.eq(
      'creditCardType',
      'Expected CreditCardType value to be equals to creditCardType'
    );
    expect(await billingInfoUpdatePage.getCvv2Input()).to.eq('cvv2', 'Expected Cvv2 value to be equals to cvv2');
    expect(await billingInfoUpdatePage.getExpiresOnInput()).to.eq('expiresOn', 'Expected ExpiresOn value to be equals to expiresOn');
    expect(await billingInfoUpdatePage.getBillingAddressInput()).to.eq(
      'billingAddress',
      'Expected BillingAddress value to be equals to billingAddress'
    );
    expect(await billingInfoUpdatePage.getCityInput()).to.eq('city', 'Expected City value to be equals to city');
    expect(await billingInfoUpdatePage.getCountryInput()).to.eq('country', 'Expected Country value to be equals to country');
    expect(await billingInfoUpdatePage.getPostalCodeInput()).to.eq('5', 'Expected postalCode value to be equals to 5');
    await billingInfoUpdatePage.save();
    expect(await billingInfoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await billingInfoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last BillingInfo', async () => {
    const nbButtonsBeforeDelete = await billingInfoComponentsPage.countDeleteButtons();
    await billingInfoComponentsPage.clickOnLastDeleteButton();

    billingInfoDeleteDialog = new BillingInfoDeleteDialog();
    expect(await billingInfoDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Billing Info?');
    await billingInfoDeleteDialog.clickOnConfirmButton();

    expect(await billingInfoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});

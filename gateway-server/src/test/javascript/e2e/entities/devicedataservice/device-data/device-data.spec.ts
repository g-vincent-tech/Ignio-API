/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { DeviceDataComponentsPage, DeviceDataDeleteDialog, DeviceDataUpdatePage } from './device-data.page-object';

const expect = chai.expect;

describe('DeviceData e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let deviceDataUpdatePage: DeviceDataUpdatePage;
  let deviceDataComponentsPage: DeviceDataComponentsPage;
  let deviceDataDeleteDialog: DeviceDataDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load DeviceData', async () => {
    await navBarPage.goToEntity('device-data');
    deviceDataComponentsPage = new DeviceDataComponentsPage();
    await browser.wait(ec.visibilityOf(deviceDataComponentsPage.title), 5000);
    expect(await deviceDataComponentsPage.getTitle()).to.eq('Device Data');
  });

  it('should load create DeviceData page', async () => {
    await deviceDataComponentsPage.clickOnCreateButton();
    deviceDataUpdatePage = new DeviceDataUpdatePage();
    expect(await deviceDataUpdatePage.getPageTitle()).to.eq('Create or edit a Device Data');
    await deviceDataUpdatePage.cancel();
  });

  it('should create and save DeviceData', async () => {
    const nbButtonsBeforeCreate = await deviceDataComponentsPage.countDeleteButtons();

    await deviceDataComponentsPage.clickOnCreateButton();
    await promise.all([deviceDataUpdatePage.setDeviceIdInput('deviceId'), deviceDataUpdatePage.setEpochInput('epoch')]);
    expect(await deviceDataUpdatePage.getDeviceIdInput()).to.eq('deviceId', 'Expected DeviceId value to be equals to deviceId');
    expect(await deviceDataUpdatePage.getEpochInput()).to.eq('epoch', 'Expected Epoch value to be equals to epoch');
    await deviceDataUpdatePage.save();
    expect(await deviceDataUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await deviceDataComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last DeviceData', async () => {
    const nbButtonsBeforeDelete = await deviceDataComponentsPage.countDeleteButtons();
    await deviceDataComponentsPage.clickOnLastDeleteButton();

    deviceDataDeleteDialog = new DeviceDataDeleteDialog();
    expect(await deviceDataDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Device Data?');
    await deviceDataDeleteDialog.clickOnConfirmButton();

    expect(await deviceDataComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});

/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { DeviceComponentsPage, DeviceDeleteDialog, DeviceUpdatePage } from './device.page-object';

const expect = chai.expect;

describe('Device e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let deviceUpdatePage: DeviceUpdatePage;
  let deviceComponentsPage: DeviceComponentsPage;
  let deviceDeleteDialog: DeviceDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Devices', async () => {
    await navBarPage.goToEntity('device');
    deviceComponentsPage = new DeviceComponentsPage();
    await browser.wait(ec.visibilityOf(deviceComponentsPage.title), 5000);
    expect(await deviceComponentsPage.getTitle()).to.eq('Devices');
  });

  it('should load create Device page', async () => {
    await deviceComponentsPage.clickOnCreateButton();
    deviceUpdatePage = new DeviceUpdatePage();
    expect(await deviceUpdatePage.getPageTitle()).to.eq('Create or edit a Device');
    await deviceUpdatePage.cancel();
  });

  it('should create and save Devices', async () => {
    const nbButtonsBeforeCreate = await deviceComponentsPage.countDeleteButtons();

    await deviceComponentsPage.clickOnCreateButton();
    await promise.all([
      deviceUpdatePage.setDeviceIdInput('deviceId'),
      deviceUpdatePage.setOwnerIdInput('ownerId'),
      deviceUpdatePage.setCreatedInput('2000-12-31'),
      deviceUpdatePage.setPurchasedInput('2000-12-31')
    ]);
    expect(await deviceUpdatePage.getDeviceIdInput()).to.eq('deviceId', 'Expected DeviceId value to be equals to deviceId');
    expect(await deviceUpdatePage.getOwnerIdInput()).to.eq('ownerId', 'Expected OwnerId value to be equals to ownerId');
    expect(await deviceUpdatePage.getCreatedInput()).to.eq('2000-12-31', 'Expected created value to be equals to 2000-12-31');
    expect(await deviceUpdatePage.getPurchasedInput()).to.eq('2000-12-31', 'Expected purchased value to be equals to 2000-12-31');
    await deviceUpdatePage.save();
    expect(await deviceUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await deviceComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Device', async () => {
    const nbButtonsBeforeDelete = await deviceComponentsPage.countDeleteButtons();
    await deviceComponentsPage.clickOnLastDeleteButton();

    deviceDeleteDialog = new DeviceDeleteDialog();
    expect(await deviceDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Device?');
    await deviceDeleteDialog.clickOnConfirmButton();

    expect(await deviceComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});

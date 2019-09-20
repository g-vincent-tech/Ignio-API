/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { SensorDataComponentsPage, SensorDataDeleteDialog, SensorDataUpdatePage } from './sensor-data.page-object';

const expect = chai.expect;

describe('SensorData e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let sensorDataUpdatePage: SensorDataUpdatePage;
  let sensorDataComponentsPage: SensorDataComponentsPage;
  let sensorDataDeleteDialog: SensorDataDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load SensorData', async () => {
    await navBarPage.goToEntity('sensor-data');
    sensorDataComponentsPage = new SensorDataComponentsPage();
    await browser.wait(ec.visibilityOf(sensorDataComponentsPage.title), 5000);
    expect(await sensorDataComponentsPage.getTitle()).to.eq('Sensor Data');
  });

  it('should load create SensorData page', async () => {
    await sensorDataComponentsPage.clickOnCreateButton();
    sensorDataUpdatePage = new SensorDataUpdatePage();
    expect(await sensorDataUpdatePage.getPageTitle()).to.eq('Create or edit a Sensor Data');
    await sensorDataUpdatePage.cancel();
  });

  it('should create and save SensorData', async () => {
    const nbButtonsBeforeCreate = await sensorDataComponentsPage.countDeleteButtons();

    await sensorDataComponentsPage.clickOnCreateButton();
    await promise.all([
      sensorDataUpdatePage.setDeviceIdInput('deviceId'),
      sensorDataUpdatePage.setTemperatureInput('5'),
      sensorDataUpdatePage.setCo_ppmInput('5'),
      sensorDataUpdatePage.setLp_gas_ppmInput('5'),
      sensorDataUpdatePage.setParticle_ppmInput('5'),
      sensorDataUpdatePage.setEpochInput('epoch')
    ]);
    expect(await sensorDataUpdatePage.getDeviceIdInput()).to.eq('deviceId', 'Expected DeviceId value to be equals to deviceId');
    expect(await sensorDataUpdatePage.getTemperatureInput()).to.eq('5', 'Expected temperature value to be equals to 5');
    expect(await sensorDataUpdatePage.getCo_ppmInput()).to.eq('5', 'Expected co_ppm value to be equals to 5');
    expect(await sensorDataUpdatePage.getLp_gas_ppmInput()).to.eq('5', 'Expected lp_gas_ppm value to be equals to 5');
    expect(await sensorDataUpdatePage.getParticle_ppmInput()).to.eq('5', 'Expected particle_ppm value to be equals to 5');
    expect(await sensorDataUpdatePage.getEpochInput()).to.eq('epoch', 'Expected Epoch value to be equals to epoch');
    await sensorDataUpdatePage.save();
    expect(await sensorDataUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await sensorDataComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last SensorData', async () => {
    const nbButtonsBeforeDelete = await sensorDataComponentsPage.countDeleteButtons();
    await sensorDataComponentsPage.clickOnLastDeleteButton();

    sensorDataDeleteDialog = new SensorDataDeleteDialog();
    expect(await sensorDataDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Sensor Data?');
    await sensorDataDeleteDialog.clickOnConfirmButton();

    expect(await sensorDataComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});

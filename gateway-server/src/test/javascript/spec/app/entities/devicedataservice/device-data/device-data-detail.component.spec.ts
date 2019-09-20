/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IgniogatewayTestModule } from '../../../../test.module';
import { DeviceDataDetailComponent } from 'app/entities/devicedataservice/device-data/device-data-detail.component';
import { DeviceData } from 'app/shared/model/devicedataservice/device-data.model';

describe('Component Tests', () => {
  describe('DeviceData Management Detail Component', () => {
    let comp: DeviceDataDetailComponent;
    let fixture: ComponentFixture<DeviceDataDetailComponent>;
    const route = ({ data: of({ deviceData: new DeviceData('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IgniogatewayTestModule],
        declarations: [DeviceDataDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DeviceDataDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DeviceDataDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.deviceData).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});

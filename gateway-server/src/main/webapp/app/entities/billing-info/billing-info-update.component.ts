import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IBillingInfo, BillingInfo } from 'app/shared/model/billing-info.model';
import { BillingInfoService } from './billing-info.service';

@Component({
  selector: 'jhi-billing-info-update',
  templateUrl: './billing-info-update.component.html'
})
export class BillingInfoUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    ignios: [],
    creditCardNumber: [],
    creditCardType: [],
    cvv2: [],
    expiresOn: [],
    billingAddress: [],
    city: [],
    country: [],
    postalCode: []
  });

  constructor(protected billingInfoService: BillingInfoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ billingInfo }) => {
      this.updateForm(billingInfo);
    });
  }

  updateForm(billingInfo: IBillingInfo) {
    this.editForm.patchValue({
      id: billingInfo.id,
      ignios: billingInfo.ignios,
      creditCardNumber: billingInfo.creditCardNumber,
      creditCardType: billingInfo.creditCardType,
      cvv2: billingInfo.cvv2,
      expiresOn: billingInfo.expiresOn,
      billingAddress: billingInfo.billingAddress,
      city: billingInfo.city,
      country: billingInfo.country,
      postalCode: billingInfo.postalCode
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const billingInfo = this.createFromForm();
    if (billingInfo.id !== undefined) {
      this.subscribeToSaveResponse(this.billingInfoService.update(billingInfo));
    } else {
      this.subscribeToSaveResponse(this.billingInfoService.create(billingInfo));
    }
  }

  private createFromForm(): IBillingInfo {
    return {
      ...new BillingInfo(),
      id: this.editForm.get(['id']).value,
      ignios: this.editForm.get(['ignios']).value,
      creditCardNumber: this.editForm.get(['creditCardNumber']).value,
      creditCardType: this.editForm.get(['creditCardType']).value,
      cvv2: this.editForm.get(['cvv2']).value,
      expiresOn: this.editForm.get(['expiresOn']).value,
      billingAddress: this.editForm.get(['billingAddress']).value,
      city: this.editForm.get(['city']).value,
      country: this.editForm.get(['country']).value,
      postalCode: this.editForm.get(['postalCode']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBillingInfo>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}

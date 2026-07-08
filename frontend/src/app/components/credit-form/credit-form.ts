import { Component, inject, input, output } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';

import { CreditEvaluationRequest } from '../../models/credit-evaluation.model';

@Component({
    selector: 'app-credit-form',
    standalone: true,
    imports: [ReactiveFormsModule],
    templateUrl: './credit-form.html'
})
export class CreditForm {
    private readonly fb = inject(FormBuilder);

    loading = input(false);
    evaluate = output<CreditEvaluationRequest>();

    form = this.fb.group({
        cedula: ['', [Validators.required, Validators.pattern(/^\d{10}$/)]],
        monto: [null as number | null, [Validators.required, Validators.min(1), Validators.max(100000)]],
        plazo: [null as number | null, [Validators.required, Validators.pattern(/^\d+$/), Validators.min(1), Validators.max(30)]],
        salario: [null as number | null, [Validators.required, Validators.min(1), Validators.max(50000)]]
    });

    onSubmit(): void {
        if (this.form.invalid) {
            this.form.markAllAsTouched();
            return;
        }
        const value = this.form.getRawValue();
        this.evaluate.emit({
            cedula: value.cedula!,
            monto: value.monto!,
            plazo: value.plazo!,
            salario: value.salario!
        });
    }
}

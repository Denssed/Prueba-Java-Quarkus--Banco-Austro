import { Component, input } from '@angular/core';

import { CreditEvaluationResponse } from '../../models/credit-evaluation.model';

@Component({
    selector: 'app-credit-result',
    standalone: true,
    templateUrl: './credit-result.html'
})
export class CreditResult {
    result = input<CreditEvaluationResponse | null>(null);
    error = input<string | null>(null);
}

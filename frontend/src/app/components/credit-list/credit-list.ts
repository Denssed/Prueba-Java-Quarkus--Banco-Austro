import { DatePipe } from '@angular/common';
import { Component, input } from '@angular/core';

import { CreditEvaluationResponse } from '../../models/credit-evaluation.model';

@Component({
    selector: 'app-credit-list',
    standalone: true,
    imports: [DatePipe],
    templateUrl: './credit-list.html'
})
export class CreditList {
    evaluations = input<CreditEvaluationResponse[]>([]);
}

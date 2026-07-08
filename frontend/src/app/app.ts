import { Component, OnInit, signal } from '@angular/core';

import { CreditForm } from './components/credit-form/credit-form';
import { CreditList } from './components/credit-list/credit-list';
import { CreditResult } from './components/credit-result/credit-result';
import { CreditEvaluationRequest, CreditEvaluationResponse } from './models/credit-evaluation.model';
import { CreditEvaluationService } from './services/credit-evaluation.service';

@Component({
    selector: 'app-root',
    imports: [CreditForm, CreditResult, CreditList],
    templateUrl: './app.html'
})
export class App implements OnInit {
    loading = signal(false);
    error = signal<string | null>(null);
    result = signal<CreditEvaluationResponse | null>(null);
    evaluations = signal<CreditEvaluationResponse[]>([]);

    constructor(private readonly creditEvaluationService: CreditEvaluationService) {
    }

    ngOnInit(): void {
        this.cargarEvaluaciones();
    }

    onEvaluate(request: CreditEvaluationRequest): void {
        this.loading.set(true);
        this.error.set(null);
        this.result.set(null);
        this.creditEvaluationService.evaluar(request).subscribe({
            next: (response) => {
                this.result.set(response);
                this.loading.set(false);
                this.cargarEvaluaciones();
            },
            error: (err) => {
                this.error.set(err.error?.mensaje ?? 'Ocurrió un error al evaluar el crédito');
                this.loading.set(false);
            }
        });
    }

    private cargarEvaluaciones(): void {
        this.creditEvaluationService.listar().subscribe((evaluations) => this.evaluations.set(evaluations));
    }
}

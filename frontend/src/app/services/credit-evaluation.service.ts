import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { environment } from '../../environments/environment';
import { CreditEvaluationRequest, CreditEvaluationResponse } from '../models/credit-evaluation.model';

@Injectable({ providedIn: 'root' })
export class CreditEvaluationService {
    private readonly baseUrl = `${environment.apiUrl}/v1/credit-evaluations`;

    constructor(private readonly http: HttpClient) {
    }

    evaluar(request: CreditEvaluationRequest): Observable<CreditEvaluationResponse> {
        return this.http.post<CreditEvaluationResponse>(this.baseUrl, request);
    }

    listar(): Observable<CreditEvaluationResponse[]> {
        return this.http.get<CreditEvaluationResponse[]>(this.baseUrl);
    }
}

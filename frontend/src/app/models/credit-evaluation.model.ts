export interface CreditEvaluationRequest {
    cedula: string;
    monto: number;
    plazo: number;
    salario: number;
}

export interface CreditEvaluationResponse {
    id: number;
    cedula: string;
    monto: number;
    plazo: number;
    salario: number;
    score: number;
    deudaMensualTotal: number;
    cuota: number;
    aprobado: boolean;
    fechaEvaluacion: string;
}

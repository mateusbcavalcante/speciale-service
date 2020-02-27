import { ErrorHandler, Injectable } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { NotificacaoService } from '../notificacao/notificacao.service';
import { AppError } from '../../core/domain/app.error';

const enum ERROR_TYPE {
  SERVER,
  CLIENT,
  APP,
  UNKNOWN
}

@Injectable({
  providedIn: 'root',
})
export class GlobalErrorHandler implements ErrorHandler {

  constructor(private notificacaoService: NotificacaoService) { }

  async handleError(error: any) {

    const errorType = this._selectErrorType(error);
    const errorTypeMessage = this._buildErrorTypeMessage(errorType, error);

    await this.notificacaoService.showErrorToaster(errorTypeMessage);

    console.log(`Tipo de erro identificado ${errorType}`);
    console.error(error);

  }

  private _selectErrorType(error: any): ERROR_TYPE {
    if (error instanceof HttpErrorResponse) {
      return ERROR_TYPE.SERVER;
    }

    if (error instanceof AppError) {
      return ERROR_TYPE.APP;
    }

    if (error instanceof Error) {
      return ERROR_TYPE.CLIENT;
    }

    return ERROR_TYPE.UNKNOWN;

  }

  private _buildErrorTypeMessage(errorType: ERROR_TYPE, error: any): string {
    if (errorType === ERROR_TYPE.CLIENT) {
      return 'Ocorreu um problema no app. Tente novamente';

    } else if (errorType === ERROR_TYPE.SERVER) {
      return error && error.error.message ? error.error.message : 'Ocorreu um problema ao acessar dados remotos';

    } else if (errorType === ERROR_TYPE.APP) {
      return error && error.message ? error.message : 'Erro ao executar ação no aplicativo';

    } else if (errorType === ERROR_TYPE.UNKNOWN) {
      return 'Desculpe o app encontrou um problema desconhecido';
    }
  }

}

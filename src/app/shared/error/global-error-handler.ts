import { ErrorHandler, Injectable } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { NotificacaoService } from 'src/app/core';
import { AppError } from 'src/app/core/domain';

const enum ERROR_TYPE {
  SERVER,
  CLIENT,
  APP,
  UNKNOWN
}


@Injectable()
export class GlobalErrorHandler implements ErrorHandler {

  constructor(private notificacaoService: NotificacaoService) { }

  async handleError(error: any) {

    const errorType = this._selectHandlerErrorStrategy(error);

    if (errorType === ERROR_TYPE.CLIENT) {
      await this.notificacaoService.showErrorToaster('Ops! Ocorreu um problema no app. Tente novamente');

    } else if (errorType === ERROR_TYPE.SERVER) {
      await this.notificacaoService.showErrorToaster(error.error.message);

    } else if (errorType === ERROR_TYPE.APP) {
      await this.notificacaoService.showErrorToaster(error.message);

    } else if (errorType === ERROR_TYPE.UNKNOWN) {
      await this.notificacaoService.showErrorToaster('Ops! Desculpe o app encontrou um problema desconhecido');
    }

    console.error('ERRO => %o', error);

  }

  private _selectHandlerErrorStrategy(error: any): ERROR_TYPE {
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

}

import {
    HttpRequest,
    HttpHandler,
    HttpEvent,
    HttpInterceptor,
    HttpResponse,
    HttpErrorResponse
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { Injectable } from '@angular/core';
import { LoadingController } from '@ionic/angular';

@Injectable()

export class LoaderInterceptor implements HttpInterceptor {

    isLoading = false;

    constructor(
        public loadingCtrl: LoadingController
    ) { }


    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

        this.presentLoading();

        return next.handle(request).pipe(
            map((event: HttpEvent<any>) => {
                if (event instanceof HttpResponse) {
                    this.dismissLoading();
                }
                return event;
            }),
            catchError((error: HttpErrorResponse) => {
                this.dismissLoading();
                return throwError(error);
            })
        );
    }

    async presentLoading() {
        this.isLoading = true;
        return await this.loadingCtrl.create({
            mode: 'ios',
            message: 'Processando...',
            duration: 10000
        }).then(a => {
            a.present().then(() => {
                if (!this.isLoading) {
                    a.dismiss().then(() => console.log('Dismissed...'));
                }
            });
        });
    }

    async dismissLoading() {
        this.isLoading = false;
        return await this.loadingCtrl.dismiss().then(() => console.log('Dismissed...'));
    }
}
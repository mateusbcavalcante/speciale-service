import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LoadingController } from '@ionic/angular';
import { from, Observable } from 'rxjs';
import { finalize, switchMap, tap } from 'rxjs/operators';

@Injectable()

export class LoaderInterceptor implements HttpInterceptor {

    constructor(
        public loadingCtrl: LoadingController
    ) { }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return from(this.loadingCtrl.create({
            mode: 'ios',
            duration: 10000
        }))
            .pipe(
                tap((loading) => {
                    return loading.present();
                }),
                switchMap((loading) => {
                    return next.handle(request).pipe(
                        finalize(() => {
                            loading.dismiss();
                        })
                    );
                })
            );
    }
}
import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class AuthenticationInterceptor implements HttpInterceptor {
    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

        const user = JSON.parse(localStorage.getItem('user'));
        console.log(user);
        if (user != null) {
            console.log(user);
            request = request.clone({
                setHeaders: {
                    Authorization: window.btoa(user.name + ':' + user.password)
                }
            });
        }
        console.log(request);
        return next.handle(request);
    }
}

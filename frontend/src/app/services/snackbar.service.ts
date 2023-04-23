import { Injectable } from '@angular/core';
import { MatSnackBar, MatSnackBarRef } from '@angular/material/snack-bar';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class SnackbarService {
  private _message = new Subject<{ message: String; action: String }>();
  $snackBarMessage = this._message.asObservable();

  private snackBarRef;

  constructor(private _snackBar: MatSnackBar) {}

  displaySnackBar(message, action) {
    // this._message.next({ message, action });
    this.snackBarRef = this._snackBar.open(message, action, {
      duration: 2000,
    });
  }

  closeSnackBar() {
    this.snackBarRef.dismiss();
  }
}

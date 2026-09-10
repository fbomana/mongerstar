import { CanDeactivateFn, UrlTree } from '@angular/router';
import {Observable } from 'rxjs';

export function getEndpointUrl( endpoint : string ) : string {
	let current = window.location.href;
	let serverPart = current.substring( 0, current.lastIndexOf(":"));
	return serverPart + ":8080" + endpoint;
}

export type CanDeactivateType = Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree;

export interface CanComponentDeactivate {
  canDeactivate: () => CanDeactivateType;
}

export const canDeactivateGuard: CanDeactivateFn<CanComponentDeactivate> = (component: CanComponentDeactivate) => {
  return component.canDeactivate ? component.canDeactivate() : true;
};

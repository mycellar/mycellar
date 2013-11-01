//describe('securityInterceptor', function() {
//  var queue, interceptor, promise, wrappedPromise;
//
//  beforeEach(angular.mock.module('mycellar.services.security.interceptor'));
//
//  it('accepts and returns a promise', inject(function(securityInterceptor) {
//    // TODO promise mock
//    var newPromise = securityInterceptor(promise);
//    expect(promise.then).toHaveBeenCalled();
//    expect(promise.then.mostRecentCall.args[0]).toBe(null);
//    expect(newPromise).toBe(wrappedPromise);
//  }));
//
//  it('does not intercept non-401 error responses', inject(function(securityInterceptor) {
//    var httpResponse = {
//      status: 400
//    };
//    // TODO promise mock
//    securityInterceptor(promise);
//    var errorHandler = promise.then.mostRecentCall.args[1];
//    expect(errorHandler(httpResponse)).toBe(promise);
//  }));
//
//  it('intercepts 401 error responses and adds it to the retry queue', inject(function(securityInterceptor) {
//    var notAuthResponse = {
//      status: 401
//    };
//    // TODO promise mock
//    securityInterceptor(promise);
//    var errorHandler = promise.then.mostRecentCall.args[1];
//    var newPromise = errorHandler(notAuthResponse);
//    expect(queue.hasMore()).toBe(true);
//    expect(queue.retryReason()).toBe('unauthorized-server');
//  }));
//});

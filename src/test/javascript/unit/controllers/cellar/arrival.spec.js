describe("Unit testing Controller: ArrivalController", function() {
  var ctrl, scope, Stocks;

  beforeEach(function() {
    module('mycellar.controllers.cellar.arrival');
    module(function($provide) {
      $provide.value('Stocks', {});
    });
    inject(function($rootScope, $controller, _Stocks_) {
      scope = $rootScope.$new();
      Stocks = _Stocks_;
      ctrl = $controller('ArrivalController', {
        $scope: scope,
        Stocks: Stocks
      });
    });
  });

  it('should exist', function() {
    expect(!!ctrl).to.be.true;
  });
  it('should provide a save method', function() {
    expect(scope.save).to.be.defined;
  });
  it('should provide a cancel method', function() {
    expect(scope.cancel).to.be.defined;
  });
  it('should provide a add method', function() {
    expect(scope.add).to.be.defined;
  });
  it('should provide a remove method', function() {
    expect(scope.remove).to.be.defined;
  });
  it('should provide a edit method', function() {
    expect(scope.edit).to.be.defined;
  });
  it('should provide a addBottle method', function() {
    expect(scope.addBottle).to.be.defined;
  });

  it('should have working add/addBottle/remove methods', function() {
    expect(scope.arrival.arrivalBottles).to.be.empty;
    expect(scope.arrivalBottle).to.be.null;
    scope.add();
    expect(scope.arrivalBottle).not.to.be.null;
    scope.addBottle();
    expect(scope.arrivalBottle).to.be.null;
    expect(scope.arrival.arrivalBottles).not.to.be.empty;
    scope.remove(scope.arrival.arrivalBottles[0]);
    expect(scope.arrival.arrivalBottles).to.be.empty;
    expect(scope.arrivalBottle).to.be.null;
  });

  it('should have a working cancel method', function() {
    expect(scope.arrival.arrivalBottles).to.be.empty;
    scope.add();
    scope.addBottle();
    scope.add();
    scope.addBottle();
    expect(scope.arrival.arrivalBottles.length).to.equals(2);

    scope.cancel();
    expect(scope.arrival.arrivalBottles).to.be.empty;
  });

  it('should have a working save method', function() {
    Stocks.arrival = sinon.spy();
    scope.save();
    expect(Stocks.arrival.calledOnce).to.be.true;
  });

});
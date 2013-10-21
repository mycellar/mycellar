describe('Unit: Testing tableService', function() {

  beforeEach(angular.mock.module('mycellar.services.table'));
  
  it('should be exposed', inject(function(tableService) {
    expect(tableService).to.not.be.null;
  }));

  it('should have a createTableContext method', inject(function(tableService) {
    expect(tableService.createTableContext).to.not.be.null;
  }));
  
  describe('Calling createTableContext()', function() {
    
    var tableContext;
    beforeEach(inject(function(tableService) {
      tableContext = tableService.createTableContext();
    }));
    
    it('should have a sortBy method', function() {
      expect(tableContext).to.have.property('sortBy').that.is.a('function');
    });
    
    it('should have a clearFilters method', function() {
      expect(tableContext).to.have.property('clearFilters').that.is.a('function');
    });
    
    it('should have an isAsc method', function() {
      expect(tableContext).to.have.property('isAsc').that.is.a('function');
    });
    
    it('should have an isDesc method', function() {
      expect(tableContext).to.have.property('isDesc').that.is.a('function');
    });
    
    describe('Calling sortBy', function() {
      it('should sort by', function() {
        expect(tableContext.sort.properties).to.have.length(0);
        expect(tableContext.isAsc('toto')).to.be.false;
        expect(tableContext.isDesc('toto')).to.be.false;
        tableContext.sortBy('toto');
        expect(tableContext.sort.properties).to.have.length(1);
        expect(tableContext.isAsc('toto')).to.be.true;
        expect(tableContext.isDesc('toto')).to.be.false;
        tableContext.sortBy('titi');
        expect(tableContext.sort.properties).to.have.length(2).and.to.deep.equals(['toto', 'titi']);
        tableContext.sortBy('toto');
        expect(tableContext.sort.properties).to.have.length(2).and.to.deep.equals(['toto', 'titi']);
        expect(tableContext.isAsc('toto')).to.be.false;
        expect(tableContext.isDesc('toto')).to.be.true;
        tableContext.sortBy('toto');
        expect(tableContext.sort.properties).to.have.length(1).and.to.deep.equals(['titi']);
        expect(tableContext.isAsc('toto')).to.be.false;
        expect(tableContext.isDesc('toto')).to.be.false;
      });
    });
    
    describe('Calling clearFilters', function() {
      it('should clearFilters', function() {
        tableContext.filters['toto'] = 'titi';
        tableContext.clearFilters();
        expect(tableContext.filters['toto']).to.be.undefined;
      });
    });
  });
  
});
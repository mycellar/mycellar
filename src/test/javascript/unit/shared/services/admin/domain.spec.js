describe('Unit: Testing adminDomainService', function () {

  beforeEach(angular.mock.module('mycellar.services.admin.domain'));
  
  describe('Testing the provider', function() {
    var provider;
    
    beforeEach(angular.mock.module(function(adminDomainServiceProvider) {
      provider = adminDomainServiceProvider;
    }));
    
    it('should expose provider', inject(function() {
      expect(provider).to.not.be.null;
    }));
    
    it('should expose provider.forDomain', inject(function() {
      expect(provider.forDomain).to.not.be.null;
    }));
    
  });
  
  it('should be exposed', inject(function(adminDomainService) {
    expect(adminDomainService).to.not.be.null;
  }));

  it('should have a getMenu method', inject(function(adminDomainService) {
    expect(adminDomainService.getMenu).to.not.be.null;
  }));
  
  describe('Calling getMenu()', function() {
    
    beforeEach(angular.mock.module(function(adminDomainServiceProvider) {
      adminDomainServiceProvider
        .forDomain('group', 'Resource', 'Resources', 'groupLabel', 'resourcesLabel')
          .whenCrud();
    }));
    
    it('should have a menu', inject(function(adminDomainService) {
      expect(adminDomainService.getMenu()).to.deep.equals([{label: 'groupLabel', menus: [{label: 'resourcesLabel', route: '/admin/domain/group/resources'}]}]);
    }));
    
  });
  
  it('should have a listMethods method', inject(function(adminDomainService) {
    expect(adminDomainService.listMethods).to.not.be.null;
  }));
  
  describe('Calling listMethods()', function() {
    
    var scope;
    var Resource = {get: 'toto'};
    beforeEach(inject(function(adminDomainService) {
      scope = adminDomainService.listMethods('group', 'resourceName', Resource, ['sort1', 'sort2'], true, true);
    }));
    
    it('should have a tableOptions property', function() {
      expect(scope.tableOptions).to.deep.equals({itemResource: Resource.get, defaultSort: ['sort1', 'sort2']});
    });
    
    it('should have a tableContext property', function() {
      expect(scope.tableContext).to.not.be.null;
    });
    
    it('should have an errors property', function() {
      expect(scope.errors).to.deep.equals([]);
    });
    
  });
  
  it('should have a editMethods method', inject(function(adminDomainService) {
    expect(adminDomainService.editMethods).to.not.be.null;
  }));
  
  describe('Calling editMethods()', function() {
    
    var scope;
    var Resource = {get: 'toto'};
    beforeEach(inject(function(adminDomainService) {
      scope = adminDomainService.editMethods('group', 'resourceName', Resource, 'form', true);
    }));
    
    it('should have a cancel method', function() {
      expect(scope.cancel).to.not.be.null;
    });
    
    it('should have a save method', function() {
      expect(scope.save).to.not.be.null;
    });
    
  });
  
});
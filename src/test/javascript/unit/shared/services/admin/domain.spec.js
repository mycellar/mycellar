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
      adminDomainServiceProvider.forDomain({
        group: 'group',
        resourceName: 'Resource',
        resourcesName: 'Resources',
        groupLabel: 'groupLabel',
        resourcesLabel: 'resourcesLabel'
      }).whenCrud();
    }));
    
    it('should have a menu', inject(function(adminDomainService) {
      expect(adminDomainService.getMenu()).to.deep.equals([{label: 'groupLabel', menus: [{label: 'resourcesLabel', route: '/admin/domain/group/resources'}]}]);
    }));
    
  });
  
  it('should have a listMethods method', inject(function(adminDomainService) {
    expect(adminDomainService.listMethods).to.not.be.null;
  }));
  
});
package com.svn.app.core.config.security;

/*
public class CustomTokenEnhancer implements TokenEnhancer {
    @Autowired
    protected UserRepository userRepository;


    private List<TokenEnhancer> delegates = Collections.emptyList();

    public void setTokenEnhancers(List<TokenEnhancer> delegates) {
        this.delegates = delegates;
    }

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        return enhanceNew(userRepository, accessToken, authentication);
    }

    public OAuth2AccessToken enhanceNew(UserRepository userRepository, OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        DefaultOAuth2AccessToken tempResult = (DefaultOAuth2AccessToken) accessToken;

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getUserAuthentication().getPrincipal();
        User daoSUser = userRepository.findByEmailIgnoreCase(userDetails.getUsername());
        final Map<String, Object> additionalInformation = new HashMap<String, Object>();
        additionalInformation.put("id", daoSUser.getId());
        additionalInformation.put("email", daoSUser.getEmail());
        additionalInformation.put("first_name", daoSUser.getFirstName());
        additionalInformation.put("last_name", daoSUser.getLastName());
        additionalInformation.put("last_name", daoSUser.getLastName());
        //additionalInformation.put("permissions", providedFwDao.getUserDao().findPermissionsByUser(daoSUser.getId()));
        //List<String> roles = new ArrayList<String>();
        //for (Role role : providedFwDao.getUserRoleDao().findByUser(daoSUser.getId())) {
        //    roles.add(role.getRoleName());
        //}
        //additionalInformation.put("roles", roles.toArray());
        tempResult.setAdditionalInformation(additionalInformation);

        OAuth2AccessToken result = tempResult;
        for (TokenEnhancer enhancer : delegates) {
            result = enhancer.enhance(result, authentication);
        }
        return result;
    }
}
*/

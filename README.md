# KARAKOC SECURITY
Aslında controller'dan servis katmanına geçmeden araya bir middleware(interceptor) katmanı attım. javascriptte metodu oluştururken araya middleware atabiliyoruz , çok basit bir şekilde. javada da aynısının benzerini yapmaya çalıştım. basit bir login - register ve bir tane token bazlı endpoint.
## İçerik
JWT Token bazlı güvenlik
<br/>
Tokenlar , username, geçerlilik süresi ve secret key'i birleştirerek oluşturuluyor. Tokenların default geçerlilik süresi 15dk, isterseniz jwt>CustomConfigService classından hem geçerlilik süresin hemde secret keyi düzenleyebilirsiniz.
<br/>
Middleware Controller'da token kontrolleri yapılmalıdır. ModelService'e token ASLA karıştırılmamalıdır. Bu sayede token var mı , varsa token kişiye mi ait , tokenin geçerlilik süresi bitmiş mi , token sahibinin rolü admin mi değil mi kontrolü ModelService'e taşınmaz ve kalabalık etmez. Classı şişirmesin diye farkı bir yere attım. Proje büyüdükçe MiddlewareController classında private yardımcı metodlar tanımlayabilirsiniz.

<br/>
PR'lar değerlendirilecektir. Projeye katkıda bulunabilirsiniz.

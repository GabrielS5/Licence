using LicenceAPI.Models;
using Microsoft.AspNetCore.Authentication;
using Microsoft.Extensions.Logging;
using Microsoft.Extensions.Options;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http.Headers;
using System.Security.Claims;
using System.Text;
using System.Text.Encodings.Web;
using System.Threading.Tasks;

namespace LicenceAPI.Common
{
    public class MyAuthenticationHandler : AuthenticationHandler<AuthenticationSchemeOptions>
    {
        private readonly Services.IAuthenticationService authenticationService;

        public MyAuthenticationHandler(
            IOptionsMonitor<AuthenticationSchemeOptions> options,
            ILoggerFactory logger,
            UrlEncoder encoder,
            ISystemClock clock,
            Services.IAuthenticationService authenticationService)
            : base(options, logger, encoder, clock)
        {
            this.authenticationService = authenticationService;
        }

        protected override async Task<AuthenticateResult> HandleAuthenticateAsync()
        {
            try
            {
                var credentials = Encoding.UTF8.GetString(Convert
                                          .FromBase64String(AuthenticationHeaderValue
                                          .Parse(Request.Headers["Authorization"]).Parameter))
                                          .Split(':');

                var name = credentials[0];
                var password = credentials[1];

                var user = authenticationService.Login(new User { Name = name, Password = password });

                var identity = new ClaimsIdentity(new[] {
                new Claim(ClaimTypes.NameIdentifier, user.Id.ToString()),
                new Claim(ClaimTypes.Name, user.Name),
                }, Scheme.Name);

                var ticket = new AuthenticationTicket(new ClaimsPrincipal(identity), Scheme.Name);

                return AuthenticateResult.Success(ticket);
            }
            catch
            {
                return AuthenticateResult.Fail("Authentication Error");
            }
        }
    }
}

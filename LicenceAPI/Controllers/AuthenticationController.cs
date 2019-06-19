using LicenceAPI.Models;
using LicenceAPI.Services;
using Microsoft.AspNetCore.Mvc;
using System.Threading.Tasks;

namespace LicenceAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class AuthenticationController : ControllerBase
    {
        private IAuthenticationService authenticationService;

        public AuthenticationController(IAuthenticationService authenticationService)
        {
            this.authenticationService = authenticationService;
        }

        [HttpPost]
        public async Task<IActionResult> Authenticate([FromBody] User user)
        {
            return Ok(authenticationService.Login(user));
        }
    }
}

using LicenceAPI.Models;

namespace LicenceAPI.Services
{
    public interface IAuthenticationService
    {
        User Login(User user);
    }
}

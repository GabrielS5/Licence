using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using LicenceAPI.Models;

namespace LicenceAPI.Services
{
    public class AuthenticationService : IAuthenticationService
    {
        private List<User> users = new List<User>
        {
            new User { Id = Guid.NewGuid(),  Name = "admin", Password = "adminPassword" }
        };

        public User Login(User user)
        {
            return users.FirstOrDefault(f => f.Name == user.Name && f.Password == user.Password);
        }
    }
}

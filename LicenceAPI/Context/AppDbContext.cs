using LicenceAPI.Models;
using Microsoft.EntityFrameworkCore;

namespace LicenceAPI.Context
{
    public sealed class AppDbContext : DbContext
    {
        public AppDbContext(DbContextOptions<AppDbContext> options)
            : base(options)
        {
            Database.Migrate();
        }

        public DbSet<Graph> Graphs { get; set; }
        public DbSet<Models.Program> Programs { get; set; }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<Graph>()
                .Property(g => g.Pending)
                .HasDefaultValue(true);

            modelBuilder.Entity<Models.Program>()
                .Property(g => g.Pending)
                .HasDefaultValue(true);
        }
    }
}

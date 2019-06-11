using LicenceAPI.Context;
using LicenceAPI.Models;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace LicenceAPI.Services
{
    public class ProgramsService : IProgramsService
    {
        private AppDbContext context;
        private IDatastoreService datastoreService;

        public ProgramsService(AppDbContext context, IDatastoreService datastoreService)
        {
            this.context = context;
            this.datastoreService = datastoreService;
        }

        public async Task Delete(Guid id)
        {
            Models.Program program = await GetById(id);

            context.Remove(program);

            await context.SaveChangesAsync();
        }

        public async Task<List<Models.Program>> GetAll()
        {
            return context.Programs.ToList();
        }

        public Task<string> GetBlob(Models.Program program)
        {
            return datastoreService.GetBlob(program.BlobId);
        }

        public async Task<Models.Program> GetById(Guid id)
        {
            return await context.Programs.FirstOrDefaultAsync(f => f.Id == id);
        }

        public async Task Insert(Models.Program program, string text)
        {
            Guid blobId = await datastoreService.InsertBlob(text);

            program.BlobId = blobId;

            await context.AddAsync(program);
            await context.SaveChangesAsync();
        }

        public async Task Update(Models.Program program)
        {
            context.Update(program);
            await context.SaveChangesAsync();
        }
    }
}

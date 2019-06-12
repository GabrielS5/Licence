using LicenceAPI.Models;
using LicenceAPI.Services;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace LicenceAPI.Controllers
{
    [Authorize]
    [Route("api/[controller]")]
    [ApiController]
    public class ProgramsController : ControllerBase
    {
        private readonly IProgramsService programsService;

        public ProgramsController(IProgramsService programsService)
        {
            this.programsService = programsService;
        }


        [HttpGet]
        public async Task<ActionResult<IEnumerable<Models.Program>>> GetPrograms()
        {
            return Ok(await programsService.GetAll());
        }

        [HttpGet("active")]
        public async Task<ActionResult<IEnumerable<Graph>>> GetActiveGraphs()
        {
            var items = (await programsService.GetAll()).Where(w => !w.Pending)
                                          .Select(async s => new EntityDTO
                                          {
                                              Name = s.Name,
                                              Text = (await programsService.GetBlob(s))
                                          }).
                                          Select(s => s.Result);

            return Ok(items);
        }

        [HttpGet("blob")]
        public async Task<ActionResult<Blob>> GetBlob([FromQuery] Guid id)
        {
            return Ok(new Blob()
            {
                Text = await programsService.GetBlob(await programsService.GetById(id))
            });
        }

        [HttpGet("accept")]
        public async Task<ActionResult> AcceptProgram([FromQuery] Guid id)
        {
            var program = await programsService.GetById(id);

            program.Pending = false;

            await programsService.Update(program);

            return Ok();
        }


        [HttpPost]
        public async Task<IActionResult> PostProgram([FromBody] EntityDTO entity)
        {
            var item = (await programsService.GetAll()).FirstOrDefault(f => f.Name == entity.Name);

            if (item != null)
                return BadRequest();

            await programsService.Insert(new Models.Program()
            {
                Name = entity.Name
            }, entity.Text);

            return Ok();
        }

        [HttpDelete]
        public async Task<IActionResult> DeleteProgram([FromQuery] Guid id)
        {
            await programsService.Delete(id);

            return Ok();
        }

    }
}
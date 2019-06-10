using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using LicenceAPI.Context;
using LicenceAPI.Models;
using LicenceAPI.Services;

namespace LicenceAPI.Controllers
{
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

        [HttpGet("blob")]
        public async Task<ActionResult<string>> GetBlob([FromQuery] Guid id)
        {
            return Ok(await programsService.GetBlob(await programsService.GetById(id)));
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
        public async Task<IActionResult> PostProgram([FromBody] string text, [FromQuery] string name)
        {
            await programsService.Insert(new Models.Program()
            {
                Name = name
            }, text);

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
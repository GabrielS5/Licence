using Microsoft.EntityFrameworkCore.Migrations;

namespace LicenceAPI.Migrations
{
    public partial class AddedPendingField : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<bool>(
                name: "Pending",
                table: "Programs",
                nullable: false,
                defaultValue: true);

            migrationBuilder.AddColumn<bool>(
                name: "Pending",
                table: "Graphs",
                nullable: false,
                defaultValue: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "Pending",
                table: "Programs");

            migrationBuilder.DropColumn(
                name: "Pending",
                table: "Graphs");
        }
    }
}

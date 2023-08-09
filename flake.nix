{
  description = "Recipe App";

  nixConfig = {
    experimental-features = "nix-command flakes";
  };

  inputs = {
    nixpkgs.url = "github:nixos/nixpkgs/nixpkgs-23.05-darwin";
    devenv.url = "github:cachix/devenv";
  };

  outputs = inputs @ {
    self,
    flake-parts,
    ...
  }:
    flake-parts.lib.mkFlake {inherit inputs;} {
      systems = ["x86_64-linux" "x86_64-darwin" "aarch64-darwin" "aarch64-linux"];
      imports = [inputs.devenv.flakeModule];
      perSystem = {pkgs, ...}: {
        formatter = pkgs.alejandra;

        devenv.shells.default = {config, ...}: {
          languages.java = {
            enable = true;
            jdk.package = pkgs.jdk19_headless;
          };

          pre-commit.hooks.ktlint = {
            enable = true;
            name = "ktlint";
            files = "\\.kt$";
            entry = "''${pkgs.ktlint}/bin/ktlint";
          };

          pre-commit.hooks = {
            alejandra.enable = true;
          };

          scripts.gradle.exec = ''exec $DEVENV_ROOT/gradlew "$@"'';
        };
      };
    };
}

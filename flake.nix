{
  description = "Development environment";

  inputs = {
    nixpkgs.url = "github:NixOS/nixpkgs";
    flake-utils.url = "github:numtide/flake-utils";
  };

  outputs = inputs @ {
    self,
    nixpkgs,
    flake-utils,
    ...
  }:
    flake-utils.lib.eachDefaultSystem (system: let
      pkgs = import nixpkgs {inherit system;};
      jdk = pkgs.jdk19_headless;
      gradle = pkgs.writeShellScriptBin "gradle" ''exec $REPOSITORY_ROOT/gradlew "$@"'';
    in {
      devShell = pkgs.mkShellNoCC {
        buildInputs = with pkgs; [jdk gradle];
        shellHook = ''
          export REPOSITORY_ROOT=$(pwd)
        '';
      };

      # enable formatting via `nix fmt`
      formatter = pkgs.alejandra; # or nixpkgs-fmt;
    });
}

@echo off
echo off

cd "C:\Daniel\Faculdade\Ano_3\Semestre_6\PS\isos_v4\isos_v4"

call .\go01-clean.cmd

call .\go02-build.cmd

call .\go03-ISystem0.cmd

pause

cd "C:\Daniel\Faculdade\Ano_3\Semestre_6\PS\isos_v4\isos_v4\_Demo"

call .\go01-Publish-ISystem0.cmd

pause

cd "C:\Daniel\Faculdade\Ano_3\Semestre_6\PS\isos_v4\isos_v4"

call .\go04-BrowseZoo.cmd
#!/bin/bash
cd /home/kavia/workspace/code-generation/recipe-explorer-184055-184075/recipe_app_android_frontend
./gradlew lint
LINT_EXIT_CODE=$?
if [ $LINT_EXIT_CODE -ne 0 ]; then
   exit 1
fi


#!/usr/bin/env bash

# bundler install
bundler exec jekyll serve &
sleep 3
open http://localhost:4000
wait

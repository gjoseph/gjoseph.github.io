#!/usr/bin/env bash

# sudo gem install bundler
# sudo gem install nokogiri -v '1.6.8' -- --use-system-libraries
# bundler install
# what a bunch of crock

bundle exec jekyll serve &
sleep 2
open http://localhost:4000
wait


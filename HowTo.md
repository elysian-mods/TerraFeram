# How to Upload Things

Hello! Here is how you can upload the content you make in a way that best streamlines the process by which it is implemented.

## Trees

If you're trying to add anything tree-related, these are the relevant instructions. Keep scrolling for anything else.

### Log

* Texture file
    * Save the texture file as/copy it over to `assets/terra_feram/textures/block/<tree>_log.png`.
* Block state
    * Create the file `assets/terra_feram/blockstates/<tree>_log.json` with the following content:
      ```json
      {
          "variants": {
          "axis=x": {
              "model": "terra_feram:block/<tree>_log_horizontal",
              "x": 90,
              "y": 90
          },
          "axis=y": {
              "model": "terra_feram:block/<tree>_log"
          },
          "axis=z": {
              "model": "terra_feram:block/<tree>_log_horizontal",
              "x": 90
              }
          }
      }
      ```
* Block model
    * Create the file `assets/terra_feram/blockstates/<tree>_log.json` with the following content:
      ```json
      {
        "parent": "minecraft:block/cube_column",
        "textures": {
            "end": "terra_feram:block/alder_log_top",
            "side": "terra_feram:block/alder_log"
        }
      }
      ```
    * Make a duplicate of this file called `<tree>_log_horizontal.json`, and replace `cube_column` above with
      `cube_column_horizontal`.
* Item Model
    * Create the file `assets/terra_feram/models/item/<tree>_log.json` with the following content:
        ```json
        {
          "parent": "terra_feram:block/<tree>_log"
        }  
        ```
* Translation
    * Edit the file `assets/terra_feram/lang/en_us.json` and add the following line:
      ```json
      "block.terra_feram.<tree>_log": "Tree Log"
      ```

### Leaves

* Texture file
    * Save the texture file as/copy it over to `assets/terra_feram/textures/block/<tree>_leaves.png`.
* Block state
    * Create the file `assets/terra_feram/blockstates/<tree>_leaves.json` with the following content:
      ```json
      {
        "variants": {
          "": {
            "model": "terra_feram:block/alder_leaves"
          }
        }     
      }
      ```
* Block model
    * Create the file `assets/terra_feram/blockstates/<tree>_leaves.json` with the following content:
      ```json
      {
        "parent": "minecraft:block/leaves",
        "textures": {
            "all": "terra_feram:block/<tree>_leaves"
        }
      }
      ```
* Item Model
    * Create the file `assets/terra_feram/models/item/<tree>_leaves.json` with the following content:
        ```json
        {
          "parent": "terra_feram:block/<tree>_leaves"
        }  
        ```
* Translation
    * Edit the file `assets/terra_feram/lang/en_us.json` and add the following line:
      ```json
      "block.terra_feram.<tree>_leaves": "Tree Leaves"
      ```

## Blocks

The following components are necessary for a Block to appear in game, even if it doesn't (yet) do anything. Please do them if you can; they aren't too tricky.

* Texture file
  * Save the texture file as/copy it over to `assets/terra_feram/textures/block/<block_name>.png`.
  * The `<block_name>` *needs* to be consistent across all references to the Block.
* Block state
  * Create the file `assets/terra_feram/blockstates/<block_name>.json` with the following content:
    ```json
    {
        "variants": {
            "": {
                "model": "terra_feram:block/<block_name>"
            }
        }
    }
    ```
* Block model
  * For most Blocks, create the file `assets/terra_feram/models/block/<block_name>.json` with the following content:
    ```json
    {
        "parent": "minecraft:block/cube_all",
        "textures": {
            "all": "terra_feram:block/<block_name>"
        }
    }
       ```
* Item Model
  * For most Blocks, you'll want an associated item, so create the file `assets/terra_feram/models/item/<block_name>.json` with the same content as above, just replace `block/cube_all` with `item/generated`.
* Translation
    * Edit the file `assets/terra_feram/lang/en_us.json` and add the following line:
      ```json
      "block.terra_feram.<block_name>": "Block Name"
      ```

## Items

The following components are necessary for an Item to appear in game, even if it doesn't (yet) do anything. Please 
do them if you can; they aren't too tricky. Also, if this is just an Item version of a Block, refer to the above.

* Texture file
  * Save the texture file as/copy it over to `assets/terra_feram/textures/item/<item_name>.png`.
  * The `<item_name>` *needs* to be consistent across all references to the Item.
* Item model
  * For most Items, create the file `assets/terra_feram/models/item/<item_name>.json` with the following content:
    ```json
    {
        "parent": "minecraft:item/generated",
        "textures": {
            "layer0": "terra_feram:item/<item_name>"
        }
    }
    ```
  * If the Item is a tool, replace `item/generated` with `item/handheld` (this may need some tweaking).
* Translation
  * Edit the file `assets/terra_feram/lang/en_us.json` and add the following line:
    ```json
    "item.terra_feram.<item_name>": "Item Name"
    ```
    
## Entities

Entities are hard. Let's collaborate on those.

## Recipes

Recipes *aren't* hard, but we haven't really thought about them in general yet, so let's collaborate on those for now.

## Structures

Structures are hard. Let's collaborate on those.
    
## Implementation

There are two possibilities here:
1. We've already discussed this content and the implementation is done.
   * Great! If you've uploaded everything correctly, then the new content should just work out of the box.
2. You're uploading a new idea proactively and need to tell me about it.
    * Edit the file `WorkQueue.md` and a short description of whatever it is you uploaded to the *end* of the file 
      if its the sort of thing you can communicate easily. A sample description is at the top.
    * Otherwise, just wait until we can discuss it.

Sorry this sounds kinda bossy, but I do think it'd be good to get a sort-of workflow going if we intend to actually finish this.